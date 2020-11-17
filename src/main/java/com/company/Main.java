package com.company;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {

    static List<Label> labels = Arrays.asList(
            new Label("Submitted", "#428BCA"),
            new Label("Resubmitted", "#0033CC"),
            new Label("Need to fix", "#D9534F"),
            new Label("In review", "#FED74A"),
            new Label("Approved", "#5CB85C"));


    public static void main(String[] args) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Project id:");
        String projectId = buffer.readLine();
        System.out.println("Token:");
        String token = buffer.readLine();
        System.out.println("URL:");
        String url = buffer.readLine();

        for (Label label : labels) {
            HttpResponse httpResponse = Request.Post(url + "/api/v4/projects/" + projectId + "/labels")
                    .addHeader("PRIVATE-TOKEN", token)
                    .bodyForm(Form.form().add("name", label.getName())
                            .add("color", label.getColor()).build())
                    .execute()
                    .returnResponse();
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
        }

        System.out.println("Press Enter to exit");
        buffer.readLine();
        buffer.close();
    }
}
