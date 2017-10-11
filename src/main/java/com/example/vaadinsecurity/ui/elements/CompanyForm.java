package com.example.vaadinsecurity.ui.elements;

import com.example.vaadinsecurity.model.Company;
import com.example.vaadinsecurity.repository.CompaniesRepository;
import com.example.vaadinsecurity.ui.AppUI;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by admin on 03.10.17.
 */
public class CompanyForm extends FormLayout {

    private final TextField name = new TextField("Наименование");
    private final TextField address = new TextField("Адрес");
    private final TextField phone = new TextField("Телефон");
    private final TextField email = new TextField("Email");
    private final CheckBox frozen = new CheckBox("Заморозка");
    private final CheckBox veggies = new CheckBox("Овощи");
    private final CheckBox seafood = new CheckBox("Морепродукты");
    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final Button hide = new Button("Свернуть");

    private CompaniesRepository repo;
    private AppUI ui;
    private Company company;
    private final Binder<Company> binder = new Binder<>(Company.class);

    public CompanyForm(AppUI ui, CompaniesRepository repo) {
        this.ui = ui;
        this.repo = repo;

        binder.bindInstanceFields(this);
        setVisible(false);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(name, address, phone, email, frozen, veggies, seafood, buttons, hide);
        save.addClickListener(e -> this.save());
        save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        delete.addClickListener(e -> this.delete());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        hide.addClickListener(e -> this.hide());
        hide.setStyleName(ValoTheme.BUTTON_QUIET);
    }

    public void setCompany(Company company) {
        this.company = company;
        binder.setBean(company);
        setVisible(true);

        delete.setVisible(company.isPersisted());
    }

    private void hide() {
        setVisible(false);
    }

    private void delete() {
        repo.delete(company);
        ui.updateList();
    }

    private void save() {
        repo.save(company);
        ui.updateList();
    }

}
