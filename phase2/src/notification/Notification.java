/*
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

//TODO: Finalized Notifications
//TODO: Account for DishStatus
//TODO: Finalized Notifications


package notification;

import java.io.IOException;

import java.util.EmptyStackException;
import java.util.Stack;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Sample custom control hosting a text field and a button.
 */
public class Notification extends Pane {


  @FXML private Label labelNotification;
  @FXML private Label labelPickUp;



  @FXML private Button buttonPickUp;
  private Stack<String> notifications;
  private final int FADETIME = 3000;
  private final double OPACITYINITIAL = 1.0;
  private final double OPACITYFINAL = 0.0;



  /**
   * Creates a new notification JavaFX control
   */
  public Notification() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notification.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    notifications = new Stack<String>();

    try {
      fxmlLoader.load();
      labelNotification.setText("");
      closeScanner();


    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }


  /**
   * Removes the most recent notification and replaces it with the next one
   * @return The String of the most recent notification
   */
  @FXML public String popNotification() {
    try {
      String message = notifications.pop();

      FadeTransition ft = new FadeTransition(Duration.millis(FADETIME), labelNotification);

      ft.setFromValue(OPACITYINITIAL);
      ft.setToValue(OPACITYFINAL);

      ft.setOnFinished(event -> {
            try { labelNotification.setText(notifications.peek()); }
            catch (EmptyStackException e){ labelNotification.setText("No New Notifications"); }
            labelNotification.setOpacity(1);
            }
      );
      ft.play();

      return message;

    } catch (EmptyStackException e) {

      return "No New Notifications";
    }
  }

  /**
   * Pushes the notification message to the top of the list. GUI will now show the most recent
   * notification
   * @param message
   */
  @FXML public void pushNotification(String message) {
    notifications.push(message);
    labelNotification.setText(message);

  }

  /**
   * Creates a confirm button.
   */
  public void openScanner(){
    labelPickUp.setVisible(true);
    buttonPickUp.setVisible(true);
  }

  /**
   * Closes the button. Makes it disappear.
   */
  public void closeScanner(){
    labelPickUp.setVisible(false);
    buttonPickUp.setVisible(false);
  }

  /**
   * Getter method for buttonPickUp.
   *
   * @return Button.
   */
  public Button getButtonPickUp() {
    return buttonPickUp;
  }

}
