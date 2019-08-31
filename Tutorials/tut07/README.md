# Tutorial 7

## Testing and JavaFX

As an exercise to learn JavaFX, create a small application that lets the user type in text and click a button to add further emphasis to that text.

To start with, implement the backend or model for the application.

* In the `examples.emphasis` package create a class `Emphasis` with a field containing some text (as a `String`) and a method that changes the field so that the text inside it is *emphasised*.
* In this example, text is emphasised by converting it to upper case and adding multiple exclamation points at the end.
* If the text already has an exclamation mark at the end, it should be considered sufficiently emphasised already and thus no change needs to be made.
* Initially, the text should be empty. Add a getter and setter for accessing and updating it.

Write some JUnit tests for this simple model.

> See code.

* What methods need to be tested?

    > In this case, only `emphasise()`. The getters and setters are too simple and have no testable properties.

* What behaviour needs to be tested?

    > The behaviour of `emphasise()` is defined by two criteria. One for when the text has no exclamation marks at the end, and one for when it does. Both alternatives need to be tested.

* **bonus challenge question** What property does the method for adding emphasis have that is useful to know for testing?

    > The `emphasise()` method is idempotent. That means executing it twice has the same result as executing it once. This can be useful for testing as it is a property of the function that is true for all inputs. See the code for how this property was used to create a parameterised test.

Change the model so that it uses a `StringProperty` instead of a `String`.

* What's the difference between the two types?

    > A `StringProperty` is a wrapper around a `String` that allows to you attach observers to it. Any time the string inside the `StringProperty` is updated, the observers are notified. Essentially, it's an easy way of using the Observer pattern for a string.

* How should the getter and setter be modified to cope with this change?

    > Having the getter return a `StringProperty` would break public interface of this class and cause our tests to no longer compile. It is better to leave the public interface as is. The `getText()` method should return the string inside the `StringProperty`. Similarly, the `setText()` method should return the string inside it.

* Can the tests be run again without modification?

    > If the class was modified in the way above, then yes.

* Add a method for accessing the `StringProperty` directly. Follow the convention JavaFX uses for this.

    > JavaFX has the convention that properties are accessed with a method that has the same name as the field with "Property" at the end. For example, `textProperty()`.

Create the UI using JavaFX SceneBuilder.

**NOTE:** You can run the JavaFX SceneBuilder from CSE computers with `2511 scenebuilder`.

* Use the empty template.
* Create a `Pane` as the root element.
* Add a `TextField` and give it an `fx:id` of `text`.
* Add a `Button` with the text "Emphasise" and give it an *On Action* handler `handleEmphasis`.

Layout the UI elements like so:

![sample screenshot](screenshot.png)

Save the FXML document in the tutorial project under the `examples.emphasis` package. You may need to refresh the project in Eclipse for the file to appear there.

Create an `EmphasisController` class to act as a controller for the UI. You can generate a skeleton of a controller from within SceneBuilder. Under *Controller* on the left menu, make the *Controller Class* `examples.emphasis.EmphasisController` then click on *View* and *Show Sample Controller Skeleton*.

* Why does one of the UI controls have a corresponding field in the controller, but the other doesn't?

    > Only controls that have been given an `fx:id` can be fields in the controller class.

* Informally, what does the `@FXML` annotation do?

    > The `@FXML` annotation connects the annotated field or method to JavaFX. That means it can be referenced in FXML files. Any fields annotated with `@FXML` will be assigned the corresponding UI element when the Controller is attached to a JavaFX `FXMLLoader`.

* Add a field for the model (`Emphasis`). Does this field need an `@FXML` annotation?

    > No. This field is not initialised by JavaFX.

* Add a constructor that creates the model. It needs to take no arguments so that JavaFX can automatically construct it when it loads the FXML file.
* Complete the `handleEmphasise()` method such that it takes the text from the text field, passes it to the model, adds the emphasis then puts it back into the text field.
* Create an empty `initialize()` method with an `@FXML` annotation. When will this method be called?

    > A method in a JavaFX controller class called `initialize()` will be called after the FXML document has been loaded.

Create an `EmphasisApplication` class that extends `javafx.application.Application`. Override the `start(...)` method to load the FXML document into a `Scene` and display it in a window. Add a `main(...)` method to launch the application. Run the application to make sure everything works.

Considering the design of the above application:

* What is wrong with the way the controller communicates between the model and the UI?

    > The text of the application is stored in two separate places, inside the model and inside the text field in the UI. Whenever the button is pressed the text in the model has to be updated with the current value from the text field and vice versa after the emphasis. Keeping two state in sync in this way is error prone and reduces understandability of code.
    >
    > The observer pattern helps resolve this problem. By using the observer pattern, we can ensure that changes to the state of the model are automatically reflected in the UI and vice versa. JavaFX makes this easy by letting us set up bidirectional bindings.

* Improve upon the design by creating a bidirectional binding between the text in the text field and the text stored in the model.
* What's the difference between a bidirectional and unidirectional binding in JavaFX?

    > Both rely on the observer pattern to ensure when one property is changed another one gets changed also. A unidirectional only makes changes in one direction; i.e. if A is unidirectionally bound to B, any changes to B are reflected in A, but attempting to change A in any other way results in a runtime error. If A is bidirectionally bound to B then changes flow both ways, any changes to A are reflected in B and any changes to B are reflectd in A.
    >
    > These relationships can be implemented manually by adding observers (listeners) to properties that update other properties, but using the `bind(..)` and `bindBidirectional(..)` methods make it clear what is going on.

* Simplify the `handleEmphasise()` method based on this change.
