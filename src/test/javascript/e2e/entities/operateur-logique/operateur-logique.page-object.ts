import { element, by, ElementFinder } from 'protractor';

export class OperateurLogiqueComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-operateur-logique div table .btn-danger'));
    title = element.all(by.css('jhi-operateur-logique div h2#page-heading span')).first();

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

export class OperateurLogiqueUpdatePage {
    pageTitle = element(by.id('jhi-operateur-logique-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    operateurLogiqueInput = element(by.id('field_operateurLogique'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setOperateurLogiqueInput(operateurLogique) {
        await this.operateurLogiqueInput.sendKeys(operateurLogique);
    }

    async getOperateurLogiqueInput() {
        return this.operateurLogiqueInput.getAttribute('value');
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

export class OperateurLogiqueDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-operateurLogique-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-operateurLogique'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
