package sample;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MailChecker {

    private boolean status = false;
    private Set<String> errors = new HashSet<>();
    public void Errors(String err){
        status = true;
        errors.add( err );
    }

    public void CheckErrors() {
        if (status) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Отправить отзыв об ошибках");
            alert.setHeaderText("Внимание! Приложение выбросило несколько ошибок. Вы хотите отправить отзыв на почту разработчика для устранения проблем? ");
            alert.setContentText("Error:" + errors.toString());
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");

            alert.getButtonTypes().setAll(yes,no);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {

                // ... user chose OK
                MailSender.SendMail("AER - Delivery 10 min - " + Config.APPLICATION_VERSION + "ver",
                        errors.toString() + " \n" + LocalDateTime.now() + "\n " +
                                "OC: "
                        );
                ;
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }

}
