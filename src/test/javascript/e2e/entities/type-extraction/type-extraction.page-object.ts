import { element, by, ElementFinder } from 'protractor';

export class TypeExtractionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-type-extraction div table .btn-danger'));
    title = element.all(by.css('jhi-type-extraction div h2#page-heading span')).first();

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

export class TypeExtractionUpdatePage {
    pageTitle = element(by.id('jhi-type-extraction-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    libelleInput = element(by.id('field_libelle'));
    monitorSelect = element(by.id('field_monitor'));
    baseSelect = element(by.id('field_base'));
    filtreSelect = element(by.id('field_filtre'));
    fluxSelect = element(by.id('field_flux'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setLibelleInput(libelle) {
        await this.libelleInput.sendKeys(libelle);
    }

    async getLibelleInput() {
        return this.libelleInput.getAttribute('value');
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

    async filtreSelectLastOption() {
        await this.filtreSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async filtreSelectOption(option) {
        await this.filtreSelect.sendKeys(option);
    }

    getFiltreSelect(): ElementFinder {
        return this.filtreSelect;
    }

    async getFiltreSelectedOption() {
        return this.filtreSelect.element(by.css('option:checked')).getText();
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

export class TypeExtractionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-typeExtraction-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-typeExtraction'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
