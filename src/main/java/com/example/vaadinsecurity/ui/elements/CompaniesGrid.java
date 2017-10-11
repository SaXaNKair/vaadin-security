package com.example.vaadinsecurity.ui.elements;

import com.example.vaadinsecurity.model.Company;
import com.vaadin.ui.Grid;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.util.List;

public class CompaniesGrid extends Grid<Company> {

    public CompaniesGrid(List<Company> companies) {
        super(Company.class);
        setItems(companies);
        removeColumn("id");
        removeColumn("frozen");
        removeColumn("veggies");
        removeColumn("seafood");
        removeColumn("persisted");


        setColumnOrder("name", "address", "phone", "email");
        getColumn("name").setCaption("Наименование организации");
        getColumn("address").setCaption("Юридически адрес");
        getColumn("phone").setCaption("Телефон");
        getColumn("email").setCaption("Эл. почта");
        setSizeFull();
        GridCellFilter filter = new GridCellFilter(this, Company.class);
        filter.setTextFilter("name", true, true);
        filter.setTextFilter("address", true, true);
    }
}
