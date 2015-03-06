package com.cs846.apis;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.cs846.code.json.Files;
import com.cs846.code.json.XChangeFile;
import com.cs946.code.api.GitHubAPI;
import com.google.gson.Gson;

@Path("/relatedfile")
public class RelatedFile {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    public RelatedFile() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves representation of an instance of RelatedFile
     * @return an instance of String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
    	Gson gson = new Gson();
    	Files f = new Files();
    	f.setFilename("Dummy1");
    	String files = gson.toJson(f);
    	return files;
    }
    
    
    @POST
    @Produces("application/json")
    public XChangeFile getFiles(String inFiles) {
    	Gson gson = new Gson();
    	XChangeFile in = gson.fromJson(inFiles, XChangeFile.class);
    	
    	
    	GitHubAPI api = new GitHubAPI();
		
    	/*
    	List<String> t = new ArrayList<String>();
    	t.add("check2");
    	in.setRelatedProductFiles(t);
    	*/
    	return api.getRelated(in);
    }

    /**
     * PUT method for updating or creating an instance of RelatedFile
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
 // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
      return "<html> " + "<title>" + "Hello Jersey" + "</title>"
          + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }

}