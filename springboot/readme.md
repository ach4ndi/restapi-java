## Test 2 - Order APIs

Use case:

- as a user, i can order a product.
- as a user, i can see my order.
- as a user, i can delete my order.

Tech stack being used on this case:

- Java, springboot with maven.
- Visual Code 1.84.7 as IDE code editor.
- Markdown Monster for markdown editor.
- Java 17.0.8 as runtime.
- Mongodb, mongoDB atlas 7.0

Database Design:
- user - `table for store user information`
- product - `table for store product information`
- order - `table for store order per product for order user id`

   | Relation Attribute Name       | Description       |
|----------------|----------------|
| OrderId (Order User table) | In this case order entry connect to order user, so user can order multiple different product on same order session. |

- orderUser - `table for store order session for user`

  | Relation Attribute Name       | Description       |
|----------------|----------------|
| UserId (User table)| In this case user can multiply ordering different product on same order session, so we need a table to connected these relation. |

Api Collection
- Stored on postman collection on repository, it containing 10 endpoint, user management (create, read, delete), product management  (create, read, delete), order management (order list by user, create new order, and delete order).

Answer:

- as a user, i can order a product.

    > Using endpoint [POST] /order to create new orders. With attribute (example):
    
        "productIds": ["654de993a527823a1fa771d8", "654de993a527823a1fa771d8"],
        "amounts": [2,15],
        "userId": "654de87ca527823a1fa771d5",
        "label": "Test order label"
    
- as a user, i can see my order.

    > Using endpoint [GET] /order/{orderId}.
    
- as a user, i can delete my order.

    > Using endpoint [Delete] /order (it will delete order session id). With attribute (example):
    
        "orderId": "654dea4ca527823a1fa771da"