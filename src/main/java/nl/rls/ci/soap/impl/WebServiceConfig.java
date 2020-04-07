//package nl.rls.ci.soap.impl;
//
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.ws.config.annotation.EnableWs;
//import org.springframework.ws.config.annotation.WsConfigurerAdapter;
//import org.springframework.ws.transport.http.MessageDispatcherServlet;
//import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
//import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
//
//@EnableWs
//@Configuration
//public class WebServiceConfig extends WsConfigurerAdapter {
////	@Bean
////	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
////		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
////		servlet.setApplicationContext(applicationContext);
////		servlet.setTransformWsdlLocations(true);
////		return new ServletRegistrationBean(servlet, "/ws/*");
////	}
////
////	@Bean(name = "ci")
////	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema messageSchema) {
////		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
////		wsdl11Definition.setPortTypeName("MessagesPort");
////		wsdl11Definition.setLocationUri("/ws");
////		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
////		wsdl11Definition.setSchema(messageSchema);
////		return wsdl11Definition;
////	}
////
////	@Bean
////	public XsdSchema messageSchema() {
////		return new SimpleXsdSchema(new ClassPathResource("message.xsd"));
////	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
//		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//		servlet.setApplicationContext(applicationContext);
//		servlet.setTransformWsdlLocations(true);
//		return new ServletRegistrationBean(servlet, "/ws/*");
//	}
//
//	@Bean(name = "ci")
//	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema messageSchema) {
//		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//		wsdl11Definition.setPortTypeName("MessagesPort");
//		wsdl11Definition.setLocationUri("/ws");
//		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
//		wsdl11Definition.setSchema(messageSchema);
//		return wsdl11Definition;
//	}
//
//	@Bean
//	public XsdSchema messageSchema() {
//		return new SimpleXsdSchema(new ClassPathResource("message.xsd"));
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
//        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//        servlet.setApplicationContext(applicationContext);
//        servlet.setTransformWsdlLocations(true);
//        return new ServletRegistrationBean(servlet, "/ws/*");
//    }
//
//    //http://localhost:8080/ws/ci.wsdl --bean name is set to 'services'
//    @Bean(name = "ci")
//    public Wsdl11Definition defaultWsdl11Definition() {
//        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
//        wsdl11Definition.setWsdl(new ClassPathResource("message.wsdl")); //your wsdl location
//        return wsdl11Definition;
//    }
//}
