import { element, by, ElementFinder } from 'protractor';

export class ClauseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-clause div table .btn-danger'));
    title = element.all(by.css('jhi-clause div h2#page-heading span')).first();

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

export class ClauseUpdatePage {
    pageTitle = element(by.id('jhi-clause-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    prefixeInput = element(by.id('field_prefixe'));
    suffixeInput = element(by.id('field_suffixe'));
    operandeSelect = element(by.id('field_operande'));
    operateurSelect = element(by.id('field_operateur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPrefixeInput(prefixe) {
        await this.prefixeInput.sendKeys(prefixe);
    }

    async getPrefixeInput() {
        return this.prefixeInput.getAttribute('value');
    }

    async setSuffixeInput(suffixe) {
        await this.suffixeInput.sendKeys(suffixe);
    }

    async getSuffixeInput() {
        return this.suffixeInput.getAttribute('value');
    }

    async operandeSelectLastOption() {
        await this.operandeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async operandeSelectOption(option) {
        await this.operandeSelect.sendKeys(option);
    }

    getOperandeSelect(): ElementFinder {
        return this.operandeSelect;
    }

    async getOperandeSelectedOption() {
        return this.operandeSelect.element(by.css('option:checked')).getText();
    }

    async operateurSelectLastOption() {
        await this.operateurSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async operateurSelectOption(option) {
        await this.operateurSelect.sendKeys(option);
    }

    getOperateurSelect(): ElementFinder {
        return this.operateurSelect;
    }

    async getOperateurSelectedOption() {
        return this.operateurSelect.element(by.css('option:checked')).getText();
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

export class ClauseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-clause-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-clause'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
