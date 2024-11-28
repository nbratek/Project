package pl.nataliabratek.project.domain.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    private static final byte[] KEY_BYTES = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static final SecretKey KEY = Keys.hmacShaKeyFor(KEY_BYTES);

    public String createToken(Integer userId) {
        String jwt = Jwts.builder()
                .subject(userId.toString())
                .signWith(KEY)
                .expiration(new Date(System.currentTimeMillis() + (15 * 60 * 1000)))
                .compact();
        return jwt;
    }

    public boolean checkToken(@Nullable String token)  {
        if(token == null){
            return false;
        }
        String normalizedToken = token.replace("Bearer ", "");
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(KEY)
                .build();
        try {
            jwtParser.parse(normalizedToken);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
    @Nullable
    public Integer getUserId(String token) {
        String normalizedToken = token.replace("Bearer ", "");
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(KEY)
                .build();
        try {
            String userId = jwtParser.parseSignedClaims(normalizedToken).getPayload().getSubject();
            return Integer.parseInt(userId);
        } catch (Exception ignored) {
            return null;
        }
    }
}

//zaprojektowac api - endpoint do dodawania, pobierania ogloszen

/*

dodawanie ogloszen - POST
@PostMapping ("/api/v1/properties")
- RequestBody:
{
  "title": "",
  "price": 0.0,
  "description" : ""
}

- ResponseBody
{
  "id": 0,
  "title": "",
  "price": 0.0,
  "description" : ""
  "createdAt" : "" (yyyy-mm-dd hh:mm:ss)
}
ResponseCode - 201 Created

pobieranie listy ogloszen - GET
@GetMapping("/api/v1/properties")
RequestParameters:
- filterByUserId(optional, Integer),
- pageNumber(optional)
- pageSize(optional)

ResponseBody:
{
  "properties" : [
    {
      "id": 0,
      "title": "",
      "price": 0.0,
      "description" : ""
      "createdAt" : "" (yyyy-mm-dd hh:mm:ss)
    }
  ],
  "totalCount" : 500

}

ResponseCode - 200 ok

pobieranie ogloszenia -GET
@GetMapping("/api/v1/properties/{id}")
- ResponseBody:
    {
      "id": 0,
      "title": "",
      "price": 0.0,
      "description" : ""
      "createdAt" : "" (yyyy-mm-dd hh:mm:ss)
    }
ResponseCode - 200 ok
aktualizacja ogłoszenia - PUT
@PutMapping("/api/v1/properties/{id}")
-RequestBody:
    {
        "title": "",
        "price": 0.0,
        "description" : ""
    }

- ResponseBody:
    {
      "id": 0,
      "title": "",
      "price": 0.0,
      "description" : ""
      "createdAt" : "" (yyyy-mm-dd hh:mm:ss)
    }
ResponseCode - 200 ok
usuwanie ogloszenia - DELETE
@DeleteMapping("/api/v1/properties/{id}")

ResponseCode - 204 NoContent

*/

/*




PropertyList
-dodanie ogloszenia do listy ulubionych

@PutMapping("/api/v1/properties/{propertyId}/favorites")

ResponseCode - 200 ok


-usuniecie ogloszenia z listy ulubionych

@DeleteMapping("/api/v1/properties/{propertyId}/favorites")

ResponseCode - 204 NoContent


-pobranie ogloszen ulubionych po id ogloszen
@GetMapping("/api/v1/properties/favourites")
RequestParameters:
- filterByPropertyId(optional, Integer, Array),


- ResponseBody:
    {
  "favoritesPropertyIds": [
    1, 2
    ]
}
ResponseCode - 200 ok



[
    1, 2
    ]




-dodawanie komentarzy
@PostMapping ("/api/v1/properties/comment")
- RequestBody:
{
  "propertyId": 0,
  "message": ""
}

- ResponseBody
{
    "userId": 0,
    "userFirstName": "",
    "userLastName": "",
    "propertyId": 0,
    "message": "",
    "createdAt": "(yyyy-mm-dd hh:mm:ss)"

}
ResponseCode - 201 Created

-pobieranie komentarzy
@GetMapping("/api/v1/properties/comment")
RequestParameters:
- filterByPropertyId(optional, Integer, Array),
- paginacja

ResponseBody:
{
    "comments": [
        {
        "userId": 0,
        "userFirstName": "",
        "userLastName": "",
        "propertyId": 0,
        "message": "",
        "createdAt": "(yyyy-mm-dd hh:mm:ss)"
        }
        ],

    "totalCounts": 0
}


-powiadomienie o nowych ogloszeniach
@PostMapping ("/api/v1/properties/notification")
- RequestBody:
{
   "location": "",
   "propertyType": ""
}

- ResponseBody
{
   "notificatonId": 0,
   "location": "",
   "propertyType": ""
}
ResponseCode - 201 Created

-zglaszanie ogloszen
@PostMapping ("/api/v1/properties/report")
- RequestBody:
{
  "propertyId": 0,
  "reason": ""
}

- ResponseBody
{
   "propertyId": 0,
   "reason": "",
   "reportedAt": "(yyyy-mm-dd hh:mm:ss)"
}



- usuwanie notyfikacji
@DeleteMapping("/api/v1/properties/notification/{notificationId}")

ResponseCode - 204 NoContent


- pobieranie notyfikacji
@GetMapping("/api/v1/properties/notification")
RequestParameters:
- paginacja


- ResponseBody:
    {
    "notifications": [
        {
            "notificationId": 0,
            "location": "",
            "propertyType": ""
        }
    ],
    "totalCount": 0
}
ResponseCode - 200 ok


pobieranie zgloszen (dla administratora)
@GetMapping("/api/v1/properties/reports")
RequestParameters:
- filterByPropertyId
- paginacja


- ResponseBody:
    {
    "reports": [
        {
            "propertyId": 0,
            "reason": "",
            "reportedAt": "(yyyy-mm-dd hh:mm:ss)",
            "reportedByUserId": 0,
            "reportedByUserEmail": ""
        }
    ],
    "totalCounts": 0
}
ResponseCode - 200 ok


tabela: property_favorites
id | propertyId | userId

 */

