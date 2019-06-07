import { element, by, ElementFinder } from 'protractor';

export class UtilisateurComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-utilisateur div table .btn-danger'));
    title = element.all(by.css('jhi-utilisateur div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UtilisateurUpdatePage {
    pageTitle = element(by.id('jhi-utilisateur-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    matriculeInput = element(by.id('field_matricule'));
    usernameInput = element(by.id('field_username'));
    firstNameInput = element(by.id('field_firstName'));
    lastNameInput = element(by.id('field_lastName'));
    emailInput = element(by.id('field_email'));
    estActifInput = element(by.id('field_estActif'));
    userSelect = element(by.id('field_user'));
    droitAccesSelect = element(by.id('field_droitAcces'));
    filialeSelect = element(by.id('field_filiale'));
    profilSelect = element(by.id('field_profil'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMatriculeInput(matricule) {
        await this.matriculeInput.sendKeys(matricule);
    }

    async getMatriculeInput() {
        return this.matriculeInput.getAttribute('value');
    }

    async setUsernameInput(username) {
        await this.usernameInput.sendKeys(username);
    }

    async getUsernameInput() {
        return this.usernameInput.getAttribute('value');
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    getEstActifInput() {
        return this.estActifInput;
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    async droitAccesSelectLastOption() {
        await this.droitAccesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async droitAccesSelectOption(option) {
        await this.droitAccesSelect.sendKeys(option);
    }

    getDroitAccesSelect(): ElementFinder {
        return this.droitAccesSelect;
    }

    async getDroitAccesSelectedOption() {
        return this.droitAccesSelect.element(by.css('option:checked')).getText();
    }

    async filialeSelectLastOption() {
        await this.filialeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async filialeSelectOption(option) {
        await this.filialeSelect.sendKeys(option);
    }

    getFilialeSelect(): ElementFinder {
        return this.filialeSelect;
    }

    async getFilialeSelectedOption() {
        return this.filialeSelect.element(by.css('option:checked')).getText();
    }

    async profilSelectLastOption() {
        await this.profilSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async profilSelectOption(option) {
        await this.profilSelect.sendKeys(option);
    }

    getProfilSelect(): ElementFinder {
        return this.profilSelect;
    }

    async getProfilSelectedOption() {
        return this.profilSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class UtilisateurDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-utilisateur-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-utilisateur'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
