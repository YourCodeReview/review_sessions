package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExampleService {

    public String greeting(String name){
        return "Hello " + name;
    }
}
