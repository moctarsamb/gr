import { element, by, ElementFinder } from 'protractor';

export class ValeurComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-valeur div table .btn-danger'));
    title = element.all(by.css('jhi-valeur div h2#page-heading span')).first();

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

export class ValeurUpdatePage {
    pageTitle = element(by.id('jhi-valeur-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    valeurInput = element(by.id('field_valeur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setValeurInput(valeur) {
        await this.valeurInput.sendKeys(valeur);
    }

    async getValeurInput() {
        return this.valeurInput.getAttribute('value');
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

export class ValeurDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-valeur-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-valeur'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
