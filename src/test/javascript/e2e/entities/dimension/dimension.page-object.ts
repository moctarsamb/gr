import { element, by, ElementFinder } from 'protractor';

export class DimensionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-dimension div table .btn-danger'));
    title = element.all(by.css('jhi-dimension div h2#page-heading span')).first();

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

export class DimensionUpdatePage {
    pageTitle = element(by.id('jhi-dimension-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    monitorSelect = element(by.id('field_monitor'));
    baseSelect = element(by.id('field_base'));
    fluxSelect = element(by.id('field_flux'));
    typeExtractionSelect = element(by.id('field_typeExtraction'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async monitorSelectLastOption() {
        await this.monitorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async monitorSelectOption(option) {
        await this.monitorSelect.sendKeys(option);
    }

    getMonitorSelect(): ElementFinder {
        return this.monitorSelect;
    }

    async getMonitorSelectedOption() {
        return this.monitorSelect.element(by.css('option:checked')).getText();
    }

    async baseSelectLastOption() {
        await this.baseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async baseSelectOption(option) {
        await this.baseSelect.sendKeys(option);
    }

    getBaseSelect(): ElementFinder {
        return this.baseSelect;
    }

    async getBaseSelectedOption() {
        return this.baseSelect.element(by.css('option:checked')).getText();
    }

    async fluxSelectLastOption() {
        await this.fluxSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async fluxSelectOption(option) {
        await this.fluxSelect.sendKeys(option);
    }

    getFluxSelect(): ElementFinder {
        return this.fluxSelect;
    }

    async getFluxSelectedOption() {
        return this.fluxSelect.element(by.css('option:checked')).getText();
    }

    async typeExtractionSelectLastOption() {
        await this.typeExtractionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typeExtractionSelectOption(option) {
        await this.typeExtractionSelect.sendKeys(option);
    }

    getTypeExtractionSelect(): ElementFinder {
        return this.typeExtractionSelect;
    }

    async getTypeExtractionSelectedOption() {
        return this.typeExtractionSelect.element(by.css('option:checked')).getText();
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

export class DimensionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-dimension-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-dimension'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
