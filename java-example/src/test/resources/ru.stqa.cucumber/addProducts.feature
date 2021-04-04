Feature: Operations with products

  Scenario: Add products to cart
    When we add some product to cart
    Then number of items in the cart is growing on '1'
    When we delete all products from cart
    Then cart is empty