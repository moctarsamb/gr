import { element, by, ElementFinder } from 'protractor';

export class ChampsRechercheComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-champs-recherche div table .btn-danger'));
    title = element.all(by.css('jhi-champs-recherche div h2#page-heading span')).first();

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

export class ChampsRechercheUpdatePage {
    pageTitle = element(by.id('jhi-champs-recherche-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    champsInput = element(by.id('field_champs'));
    dateDebutExtractionInput = element(by.id('field_dateDebutExtraction'));
    dateFinExtractionInput = element(by.id('field_dateFinExtraction'));
    colonneSelect = element(by.id('field_colonne'));
    environnementSelect = element(by.id('field_environnement'));
    filialeSelect = element(by.id('field_filiale'));
    requisitionSelect = element(by.id('field_requisition'));
    typeExtractionSelect = element(by.id('field_typeExtraction'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setChampsInput(champs) {
        await this.champsInput.sendKeys(champs);
    }

    async getChampsInput() {
        return this.champsInput.getAttribute('value');
    }

    async setDateDebutExtractionInput(dateDebutExtraction) {
        await this.dateDebutExtractionInput.sendKeys(dateDebutExtraction);
    }

    async getDateDebutExtractionInput() {
        return this.dateDebutExtractionInput.getAttribute('value');
    }

    async setDateFinExtractionInput(dateFinExtraction) {
        await this.dateFinExtractionInput.sendKeys(dateFinExtraction);
    }

    async getDateFinExtractionInput() {
        return this.dateFinExtractionInput.getAttribute('value');
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

    async environnementSelectLastOption() {
        await this.environnementSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async environnementSelectOption(option) {
        await this.environnementSelect.sendKeys(option);
    }

    getEnvironnementSelect(): ElementFinder {
        return this.environnementSelect;
    }

    async getEnvironnementSelectedOption() {
        return this.environnementSelect.element(by.css('option:checked')).getText();
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

    async requisitionSelectLastOption() {
        await this.requisitionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async requisitionSelectOption(option) {
        await this.requisitionSelect.sendKeys(option);
    }

    getRequisitionSelect(): ElementFinder {
        return this.requisitionSelect;
    }

    async getRequisitionSelectedOption() {
        return this.requisitionSelect.element(by.css('option:checked')).getText();
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

export class ChampsRechercheDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-champsRecherche-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-champsRecherche'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
