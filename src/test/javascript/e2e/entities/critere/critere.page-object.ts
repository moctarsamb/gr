import { element, by, ElementFinder } from 'protractor';

export class CritereComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-critere div table .btn-danger'));
    title = element.all(by.css('jhi-critere div h2#page-heading span')).first();

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

export class CritereUpdatePage {
    pageTitle = element(by.id('jhi-critere-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    clauseSelect = element(by.id('field_clause'));
    operateurLogiqueSelect = element(by.id('field_operateurLogique'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async clauseSelectLastOption() {
        await this.clauseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clauseSelectOption(option) {
        await this.clauseSelect.sendKeys(option);
    }

    getClauseSelect(): ElementFinder {
        return this.clauseSelect;
    }

    async getClauseSelectedOption() {
        return this.clauseSelect.element(by.css('option:checked')).getText();
    }

    async operateurLogiqueSelectLastOption() {
        await this.operateurLogiqueSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async operateurLogiqueSelectOption(option) {
        await this.operateurLogiqueSelect.sendKeys(option);
    }

    getOperateurLogiqueSelect(): ElementFinder {
        return this.operateurLogiqueSelect;
    }

    async getOperateurLogiqueSelectedOption() {
        return this.operateurLogiqueSelect.element(by.css('option:checked')).getText();
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

export class CritereDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-critere-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-critere'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
