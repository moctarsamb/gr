import { element, by, ElementFinder } from 'protractor';

export class MonitorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-monitor div table .btn-danger'));
    title = element.all(by.css('jhi-monitor div h2#page-heading span')).first();

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

export class MonitorUpdatePage {
    pageTitle = element(by.id('jhi-monitor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    typeSelect = element(by.id('field_type'));
    colonneSelect = element(by.id('field_colonne'));
    fonctionSelect = element(by.id('field_fonction'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTypeSelect(type) {
        await this.typeSelect.sendKeys(type);
    }

    async getTypeSelect() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async colonneSelectLastOption() {
        await this.colonneSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async colonneSelectOption(option) {
        await this.colonneSelect.sendKeys(option);
    }

    getColonneSelect(): ElementFinder {
        return this.colonneSelect;
    }

    async getColonneSelectedOption() {
        return this.colonneSelect.element(by.css('option:checked')).getText();
    }

    async fonctionSelectLastOption() {
        await this.fonctionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async fonctionSelectOption(option) {
        await this.fonctionSelect.sendKeys(option);
    }

    getFonctionSelect(): ElementFinder {
        return this.fonctionSelect;
    }

    async getFonctionSelectedOption() {
        return this.fonctionSelect.element(by.css('option:checked')).getText();
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

export class MonitorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-monitor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-monitor'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
