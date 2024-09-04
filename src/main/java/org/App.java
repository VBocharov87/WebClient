package org;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    public static RestTemplate restTemplate = new RestTemplate();
    public static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

        ResponseEntity<String> response =
                restTemplate.getForEntity(URL, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);
        String sessionId = null;
        for (String cookie : cookies) {
            if (cookie.startsWith("JSESSIONID")) {
                sessionId = cookie.split(";")[0];
                System.out.println("Session ID: " + sessionId);
            }
        }

        User user1 = new User(3L, "James", "Brown", (byte) 30);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);
        HttpEntity<User> entity = new HttpEntity<>(user1, headers);

        ResponseEntity<String> addUserResponse = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println(addUserResponse.getBody());

        //----------------------------------------------------------------------------

        User user2 = new User(3L, "Thomas", "Shelby", (byte) 30);

        HttpEntity<User> entity2 = new HttpEntity<>(user2, headers);

        ResponseEntity<String> updateUserResponse = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                entity2,
                String.class
        );

        System.out.println(updateUserResponse.getBody());

        //----------------------------------------------------------------------------

        HttpEntity<Void> entity3 = new HttpEntity<>(headers);

        ResponseEntity<String> deleteUserResponse = restTemplate.exchange(
                URL + "/3",
                HttpMethod.DELETE,
                entity3,
                String.class
        );

        System.out.println(deleteUserResponse.getBody());

//        JSESSIONID=E127A863AA4E17E345798434B2EADD7E; Path=/; HttpOnly
//        HttpHeaders headers = new HttpHeaders();
//        List<String> cookies =
//                headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
    }

    public static void getAllUsers() {

    }
}
