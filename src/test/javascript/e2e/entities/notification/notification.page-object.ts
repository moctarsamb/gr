import { element, by, ElementFinder } from 'protractor';

export class NotificationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-notification div table .btn-danger'));
    title = element.all(by.css('jhi-notification div h2#page-heading span')).first();

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

export class NotificationUpdatePage {
    pageTitle = element(by.id('jhi-notification-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    libelleInput = element(by.id('field_libelle'));
    descriptionInput = element(by.id('field_description'));
    estOuvertInput = element(by.id('field_estOuvert'));
    utilisateurSelect = element(by.id('field_utilisateur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLibelleInput(libelle) {
        await this.libelleInput.sendKeys(libelle);
    }

    async getLibelleInput() {
        return this.libelleInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    getEstOuvertInput() {
        return this.estOuvertInput;
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

export class NotificationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-notification-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-notification'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
