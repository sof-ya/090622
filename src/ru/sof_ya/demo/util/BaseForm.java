package ru.sof_ya.demo.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    private String APP_TITLE = "Посуда";
    private Image APP_ICON = null;

    public BaseForm(int weigh, int height) {
        try {
            APP_ICON = ImageIO.read(BaseForm.class.getClassLoader().getResource("вых.png"));
        } catch (IOException e) {
            e.printStackTrace();
            DialogUtil.showError(null, "Ошибка загрузки иконки");
        }

        setMinimumSize(new Dimension(weigh, height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - weigh / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);
        setTitle(APP_TITLE);
        setIconImage(APP_ICON);
    }
}
