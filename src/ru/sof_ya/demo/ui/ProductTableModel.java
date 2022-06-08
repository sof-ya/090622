package ru.sof_ya.demo.ui;

import ru.sof_ya.demo.entity.ProductEntity;
import ru.sof_ya.demo.manager.ProductEntityManager;
import ru.sof_ya.demo.util.BaseForm;
import ru.sof_ya.demo.util.CustomTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ProductTableModel extends BaseForm {
    private JButton addButton;
    private JTable table;
    private JPanel mainPanel;

    private CustomTableModel<ProductEntity> model;

    public ProductTableModel()
    {
        super(800, 600);
        setContentPane(mainPanel);
        initTable();
        initButtons();
        setVisible(true);
    }

    private void initTable()
    {
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);

        try {
            model = new CustomTableModel<>(
                    ProductEntity.class,
                    new String[] {"ID", "Название", "Тип", "Артикул", "Описание", "Путь к картинке", "Количество работников", "Номер цеха", "Стоимость"},
                    ProductEntityManager.selectAll()
            );
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    int row = table.rowAtPoint(e.getPoint());
                    {
                        if(row != -1)
                        {
                            dispose();
                            new ProductEditForm(model.getRows().get(row));
                        }
                    }
                }
            }
        });
    }

    private void initButtons()
    {
        addButton.addActionListener(e ->
        {
            dispose();
            new ProductCreateForm();
        });
    }
}
