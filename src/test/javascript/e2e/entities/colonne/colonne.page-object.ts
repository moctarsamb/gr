import { element, by, ElementFinder } from 'protractor';

export class ColonneComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-colonne div table .btn-danger'));
    title = element.all(by.css('jhi-colonne div h2#page-heading span')).first();

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

export class ColonneUpdatePage {
    pageTitle = element(by.id('jhi-colonne-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    libelleInput = element(by.id('field_libelle'));
    descriptionInput = element(by.id('field_description'));
    aliasInput = element(by.id('field_alias'));
    typeExtractionRequeteeSelect = element(by.id('field_typeExtractionRequetee'));
    fluxSelect = element(by.id('field_flux'));

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

    async setAliasInput(alias) {
        await this.aliasInput.sendKeys(alias);
    }

    async getAliasInput() {
        return this.aliasInput.getAttribute('value');
    }

    async typeExtractionRequeteeSelectLastOption() {
        await this.typeExtractionRequeteeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typeExtractionRequeteeSelectOption(option) {
        await this.typeExtractionRequeteeSelect.sendKeys(option);
    }

    getTypeExtractionRequeteeSelect(): ElementFinder {
        return this.typeExtractionRequeteeSelect;
    }

    async getTypeExtractionRequeteeSelectedOption() {
        return this.typeExtractionRequeteeSelect.element(by.css('option:checked')).getText();
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

export class ColonneDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-colonne-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-colonne'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
