package ru.sof_ya.demo.ui;

import ru.sof_ya.demo.entity.ProductEntity;
import ru.sof_ya.demo.manager.ProductEntityManager;
import ru.sof_ya.demo.ui.ProductTableModel;
import ru.sof_ya.demo.util.BaseForm;
import ru.sof_ya.demo.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ProductEditForm extends BaseForm {
    private JTextField titleField;
    private JTextField typeField;
    private JTextField articleField;
    private JTextField descriptionField;
    private JTextField imagePathField;
    private JTextField costField;
    private JButton backButton;
    private JButton saveButton;
    private JSpinner workerCountField;
    private JSpinner workshopNumField;
    private JPanel mainPanel;
    private JTextField idField;
    private JButton deleteButton;

    private ProductEntity product;

    public ProductEditForm(ProductEntity product) {
        super(400, 400);
        this.product = product;
        setContentPane(mainPanel);
        initFields();
        initButtons();
        setVisible(true);
    }

    private void initFields()
    {
        idField.setEditable(false);
        idField.setText(String.valueOf(product.getId()));
        titleField.setText(product.getTitle());
        typeField.setText(product.getType());
        articleField.setText(product.getArticleNum());
        descriptionField.setText(product.getDescription());
        imagePathField.setText(product.getImagePath());
        workerCountField.setValue(product.getPrsonCount());
        workshopNumField.setValue(product.getWorkshopNum());
        costField.setText(String.valueOf(product.getMinCost()));
    }

    private void initButtons() {
        backButton.addActionListener(e ->
        {
            dispose();
            new ProductTableModel();
        });
        saveButton.addActionListener(e ->
        {
            String title = titleField.getText();
            if (title.isEmpty() || title.length() > 100) {
                DialogUtil.showError(this, "Название не введено или слишком длинное");
                return;
            }
            String type = typeField.getText();
            if (type.isEmpty() || type.length() > 100) {
                DialogUtil.showError(this, "Тип не введен или слишком длинный");
                return;
            }
            String article = articleField.getText();
            if (article.isEmpty() || article.length() > 10) {
                DialogUtil.showError(this, "Артикул не введен или слишком длинный");
                return;
            }
            String description = descriptionField.getText();
            String imagePath = imagePathField.getText();
            if (imagePath.length() > 10) {
                DialogUtil.showError(this, "Путь к картике слишком длинный");
                return;
            }
            int workers = (int) workerCountField.getValue();
            if (workers < 0) {
                DialogUtil.showError(this, "Количество работников введено неверно");
            }
            int workshop = (int) workshopNumField.getValue();
            if (workshop < 0) {
                DialogUtil.showError(this, "Номер цеха введен неверно");
            }
            double cost = -1;
            try {
                cost = Double.parseDouble(costField.getText());
            } catch (Exception ex) {
                DialogUtil.showError(this, "Стоимость введена неверно");
                return;
            }
            if (cost < 0) {
                DialogUtil.showError(this, "Стоимость введена неверно");
                return;
            }

            product.setTitle(title);
            product.setType(type);
            product.setArticleNum(article);
            product.setDescription(description);
            product.setImagePath(imagePath);
            product.setPrsonCount(workers);
            product.setWorkshopNum(workshop);
            product.setMinCost(cost);

            try {
                ProductEntityManager.update(product);
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "Ошибка сохранения данных" + ex.getMessage());
                return;
            }
            DialogUtil.showInfo(this,"Продукт успешно отредактирован");
            dispose();
            new ProductTableModel();
        });

        deleteButton.addActionListener(e ->
        {
            if(JOptionPane.showConfirmDialog(this,"Вы точно хотите удалить данный продукт?", "Подтверждение", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                try {
                    ProductEntityManager.delete(product);
                    DialogUtil.showInfo(this, "Продукт успешно удален");
                    dispose();
                    new ProductTableModel();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    DialogUtil.showError(this, "Ошибка удаления: " + ex.getMessage());
                    return;
                }
            }
        });
    }

    ;
}
