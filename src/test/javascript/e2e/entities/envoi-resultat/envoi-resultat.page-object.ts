import { element, by, ElementFinder } from 'protractor';

export class EnvoiResultatComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-envoi-resultat div table .btn-danger'));
    title = element.all(by.css('jhi-envoi-resultat div h2#page-heading span')).first();

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

export class EnvoiResultatUpdatePage {
    pageTitle = element(by.id('jhi-envoi-resultat-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    emailInput = element(by.id('field_email'));
    dateEnvoiInput = element(by.id('field_dateEnvoi'));
    fichierResultatSelect = element(by.id('field_fichierResultat'));
    utilisateurSelect = element(by.id('field_utilisateur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setDateEnvoiInput(dateEnvoi) {
        await this.dateEnvoiInput.sendKeys(dateEnvoi);
    }

    async getDateEnvoiInput() {
        return this.dateEnvoiInput.getAttribute('value');
    }

    async fichierResultatSelectLastOption() {
        await this.fichierResultatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async fichierResultatSelectOption(option) {
        await this.fichierResultatSelect.sendKeys(option);
    }

    getFichierResultatSelect(): ElementFinder {
        return this.fichierResultatSelect;
    }

    async getFichierResultatSelectedOption() {
        return this.fichierResultatSelect.element(by.css('option:checked')).getText();
    }

    async utilisateurSelectLastOption() {
        await this.utilisateurSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async utilisateurSelectOption(option) {
        await this.utilisateurSelect.sendKeys(option);
    }

    getUtilisateurSelect(): ElementFinder {
        return this.utilisateurSelect;
    }

    async getUtilisateurSelectedOption() {
        return this.utilisateurSelect.element(by.css('option:checked')).getText();
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

export class EnvoiResultatDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-envoiResultat-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-envoiResultat'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
