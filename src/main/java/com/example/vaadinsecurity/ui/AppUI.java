package com.example.vaadinsecurity.ui;

import com.example.vaadinsecurity.model.Company;
import com.example.vaadinsecurity.model.Role;
import com.example.vaadinsecurity.repository.CompaniesRepository;
import com.example.vaadinsecurity.security.SecurityUtils;
import com.example.vaadinsecurity.ui.elements.AdminCompaniesGrid;
import com.example.vaadinsecurity.ui.elements.CompaniesGrid;
import com.example.vaadinsecurity.ui.elements.CompanyForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Theme("apptheme")
@SpringUI(path = "/app")
public class AppUI extends UI {

    private VerticalLayout root;

    @Autowired
    private CompaniesRepository companiesRepository;
    private List<Company> companyList;
    private TabSheet tabSheet;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        addRoot();
        addHeader();
        updateList();
        addFooter();
    }

    private void addFooter() {

    }

    private void addHeader() {
        Button logoutButton = new Button("Выйти");
//        logoutButton.addClickListener(); // TODO: 11.10.2017 implement logout
        Label label = new Label("БАЗА ДАННЫХ ПОСТАВЩИКОВ ПРОДУКТОВ ПИТАНИЯ");
        label.addStyleName(ValoTheme.LABEL_H1);
        label.setSizeFull();
        VerticalLayout headerDiv = new VerticalLayout();
        headerDiv.addComponents(logoutButton, label);
        headerDiv.setWidth("100%");
        headerDiv.setExpandRatio(label, 1);
        headerDiv.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        headerDiv.setId("headerDiv");
        root.addComponents(headerDiv);
    }

    private enum TYPE {
        FROZEN,
        VEGGIES,
        SEAFOOD
    }

    private CompaniesGrid getCompanies(TYPE type) {
        List<Company> selectedCompanies = new ArrayList<>();
        for(Company c : companyList) {
            if (type == TYPE.FROZEN) {
                if (c.isFrozen())
                    selectedCompanies.add(c);
            } else if (type == TYPE.VEGGIES) {
                if (c.isVeggies())
                    selectedCompanies.add(c);
            } else {
                if (c.isSeafood())
                    selectedCompanies.add(c);
            }
        }
        return new CompaniesGrid(selectedCompanies);

    }

    private void addSeafoodTab() {
        CompaniesGrid grid = getCompanies(TYPE.SEAFOOD);
        VerticalLayout tab = new VerticalLayout(grid);
        tabSheet.addTab(tab, "Морепродукты");
    }

    private void addVeggiesTab() {
        CompaniesGrid grid = getCompanies(TYPE.VEGGIES);
        VerticalLayout tab = new VerticalLayout(grid);
        tabSheet.addTab(tab, "Овощи");
    }

    private void addFrozenTab() {
        Grid<Company> grid = getCompanies(TYPE.FROZEN);
        VerticalLayout tab = new VerticalLayout(grid);
        tabSheet.addTab(tab, "Заморозка");
    }

    private void addAdminTab() {

        Grid<Company> grid = new AdminCompaniesGrid(companiesRepository.findAll());
        CompanyForm form = new CompanyForm(this, companiesRepository);

        HorizontalLayout gridAndForm = new HorizontalLayout(grid, form);
        gridAndForm.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        gridAndForm.setSizeFull();
        gridAndForm.setExpandRatio(grid, 1);
        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() == null)
                form.setVisible(false);
            else {
                form.setVisible(true);
                form.setCompany(e.getValue());
            }
        });

        Button addCompanyBtn = new Button("Добавить компанию");
        addCompanyBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCompany(new Company());
        });

        VerticalLayout adminTab = new VerticalLayout(addCompanyBtn, gridAndForm);
        adminTab.setExpandRatio(gridAndForm, 1);
        adminTab.setSizeFull();
        adminTab.setSpacing(true);
        tabSheet.addTab(adminTab, "Администрирование");
    }

    private void addTabs() {
        if (tabSheet == null)
            tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        root.addComponent(tabSheet);
        root.setExpandRatio(tabSheet, 1);
    }

    public void updateList() {
        companyList = companiesRepository.findAll();
        if (tabSheet != null)
            tabSheet.removeAllComponents();
        addTabs();
        if (SecurityUtils.isCurrentUserInRole(Role.ADMIN))
            addAdminTab();
        addFrozenTab();
        addVeggiesTab();
        addSeafoodTab();
    }

    private void addRoot() {
        root = new VerticalLayout();
        root.setId("root");
        root.setSpacing(true);
        root.setSizeFull();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }

}
