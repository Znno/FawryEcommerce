# Test Scenarios for Shopping Cart Program

## Scenario 1: Successful Purchase

**Input:**

- Customer Name: John
- Initial Balance: $2600
- Add to cart: 2 TVs
- Add to cart: 1 Phone
- Confirm order: y

**Expected Output:**

- Order Summary showing:
    - 2 TVs ($2000)
    - 1 Phone ($500)
    - Shipping fees calculated
    - Successful purchase confirmation
    - Remaining balance displayed

## Scenario 2: Insufficient Funds

**Input:**

- Customer Name: Alice
- Initial Balance: $800
- Add to cart: 1 TV
- Add to cart: 1 Phone
- Confirm order: y

**Expected Output:**

- Order Summary showing:
    - 1 TV ($1000)
    - 1 Phone ($500)
    - Shipping fees calculated
    - "Insufficient funds! Order cancelled."
    - Inventory reverted

## Scenario 3: Expired Product

**Input:**

- Customer Name: Bob
- Initial Balance: $100
- Add to cart: 1 Milk (expired)
- Confirm order: y

**Expected Output:**

- Error message about expired products
- Order cancelled
- Inventory reverted

## Scenario 4: Cancel Order

**Input:**

- Customer Name: Carol
- Initial Balance: $5000
- Add to cart: 1 TV
- Confirm order: n

**Expected Output:**

- Order Summary displayed
- "Order cancelled"
- Inventory reverted

## Scenario 5: Invalid Inputs

**Input:**

- Customer Name: "" (empty)
- Initial Balance: -100
- Product selection: 99
- Quantity: -1

**Expected Output:**

- Error messages for each invalid input
- Program handles errors gracefully

## Scenario 6: Maximum Quantity

**Input:**

- Customer Name: David
- Initial Balance: $1000
- Add to cart: 6 TVs (when only 5 available)

**Expected Output:**

- "Invalid quantity!" error message
- No changes to cart or inventory
