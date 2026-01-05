package com.space.orbit.config;

import jakarta.annotation.PostConstruct;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class OrekitConfig {

    @PostConstruct
    public void init() {
        try{
            File orekitData = new File("orekit-data");
            DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
            manager.addProvider(new DirectoryCrawler(orekitData));
        }catch(Exception e){
            throw new RuntimeException("Cannot load orbit data",e);
        }
    }
}
