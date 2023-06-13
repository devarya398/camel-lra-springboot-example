package com.camel_lra_springboot_example.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.camel_lra_springboot_example.model.Order;
import com.camel_lra_springboot_example.processor.GenerateIdempotencyIdProcessor;
import com.camel_lra_springboot_example.services.ServiceOne;
import com.camel_lra_springboot_example.services.ServiceTwo;

@Component
public class SagaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	
    	restConfiguration()
        .component("servlet")
        .bindingMode(RestBindingMode.auto);
    	
    	rest("/hello").post()
		.to("direct:hello");

    	from("direct:hello")
        .log("The body is ${body}!")
        .transform().simple("Hello World");
        
    	rest().post("/saga")
    	.type(Order.class)
	        .to("direct:saga");
    	
    	from("direct:saga")
		.log("saga starting...")
            .saga()
                .to("direct:serviceOne")
                .to("direct:serviceTwo")
            .completion("direct:sagaCompleted")
            .compensation("direct:sagaCompensated")
            .to("direct:completed")
        .end();
    
        from("direct:serviceOne")
        .process(new GenerateIdempotencyIdProcessor())
            .saga()
            .propagation(SagaPropagation.SUPPORTS)
            .option("body", body())
            .compensation("direct:serviceOneCancel")
            .bean(ServiceOne.class, "performOperation");
    
        from("direct:serviceOneCancel")
            .routeId("service-one-cancel")
            .log("Canceling one")
        	.transform(header("body"))
            .bean(ServiceOne.class, "cancelOperation");
    
        from("direct:serviceTwo")
            .saga()
            .propagation(SagaPropagation.SUPPORTS)
            .option("body", body())
            .compensation("direct:serviceTwoCancel")
            .bean(ServiceTwo.class, "performOperation");
    
        from("direct:serviceTwoCancel")
            .routeId("service-two-cancel")
            .log("Canceling two")
        	.transform(header("body"))
            .bean(ServiceTwo.class, "cancelOperation");
    
        from("direct:sagaCompleted")
            .routeId("saga-completion-route")
            .log("Saga Completed");
    
        from("direct:sagaCompensated")
            .routeId("saga-compensation-route")
            .log("Saga Compensating...");
        
        from("direct:completed")
            .setBody(constant("Completed"));
    }
    
}
