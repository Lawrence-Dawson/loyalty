# Flux Loyalty Test

### This mini app is an attempt at the Flux Loyalty Test

## As required:
- The solution is written in Kotlin (I have not previously coded anything in Java or Kotlin)
- The 'ImplementMe' interface has been implemented.
- The 'schemes' property has been set.
- The basic tests all pass.

### Setup:
- Clone this repository onto your local environment.
- Open the 'loyalty' directory in an IDE capable of running a JVM such as IntelliJ or Netbeans.
- Navigate to 'loyalty/src/test/kotlin/com.flux.test/LoyaltySpec' and run its tests.

### Additions:
- An 'Account' model with a list of 'Stamp' and 'Payment' to represent its relationship with them in the database.
- A 'Payment' model that represents a payment to an 'Account'.
- A 'Stamp' model that represents a stamp on a schemes 'Account'.
- An 'AccountService' that can retrieve and update and 'Account'.
- A 'ReceiptApplication' which handles the application of a 'Receipt' to an 'Account'.

### Note:
- Stamps always persist in memory database, when they become a payment they are given the status of 'repaid', when a payment is made the remaining stamps for that scheme are given the status of "inactive" this is good for analytics, we can always see which were created and which stamps were made into payments.

## Tests:
#### The following test scenarios have been added and pass:
- "Multiple schemes can be running for the same merchant"
- "Applies multiple receipts to same merchant"
- "Applies multiple receipts to different merchant"
- "Cheapest item in scheme given away in redemption"
- "Each item in a receipt can be used only once"
- "Item cannot be used in multiple schemes"
- "Item cannot be used in multiple schemes when one becomes payment"

# Original Task Readme Below:
 
The task is to implement a stamp card loyalty system of the type seen in many cafes and coffee shops (e.g. buy 4 coffees get the 5th free).

### Setup
An interface to implement is provided `com.flux.test.ImplementMe`.  The methods are documented to explain what is expected.  Model classes are provided to model some of the basics - please create any extra models that you want but also please don't modify the models given.

Some basic test cases are also provided in `com.flux.test.LoyaltySpec` - you are welcome to write more tests to help you, though tests are not expected as part of the task.

The `ImplementMe` interface has a `schemes` property - the calling code will set this property to represent the active schemes that should be run on any `apply` call.  The schemes will not change between runs and should be considered immutable


In the real world - this would exist as a service, communicating over REST with other services and persisting to a database.  However in order to keep the test focused on the interesting parts the following assumptions are made:
* The `ImplementMe` interface represents the API spec to implement, the return types represent the response bodies that would be used and *do not* represent an opinion on the data structure to use.
* Persistence is not required - the code should store it's state in memory and is not expected to survive restarts.
* Multiple schemes can be running for one merchant concurrently.
* When an account is due a payment the cheapest item in the scheme should be given away first.
* Each item in a receipt can only be used once, one item cannot be used for both a stamp and a payment, nor can an item be used in multiple schemes.


### Language

The boilerplate code given is written in Kotlin, the code you write is not expected to be in Kotlin.  Please use only Kotlin or Java - there is no preference from us and extra points are not given for using Kotlin - use the language you know best that is going to allow you to focus on your solution

### Tips
This is a coding test - we want the focus to be on how the code is structured and making sure that corner cases have been thought about and covered.  We will be looking at style, structure, seperation of concerns and reusability of code.

This means if the code is just one giant function - that is bad - but also if the code base contains a thousand classes which all do one line of code each - also bad.   