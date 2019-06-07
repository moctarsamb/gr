import { element, by, ElementFinder } from 'protractor';

export class ProfilComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-profil div table .btn-danger'));
    title = element.all(by.css('jhi-profil div h2#page-heading span')).first();

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

export class ProfilUpdatePage {
    pageTitle = element(by.id('jhi-profil-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    libelleInput = element(by.id('field_libelle'));
    descriptionInput = element(by.id('field_description'));
    colonneSelect = element(by.id('field_colonne'));
    typeExtractionSelect = element(by.id('field_typeExtraction'));
    administrateurProfilSelect = element(by.id('field_administrateurProfil'));

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

    async administrateurProfilSelectLastOption() {
        await this.administrateurProfilSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async administrateurProfilSelectOption(option) {
        await this.administrateurProfilSelect.sendKeys(option);
    }

    getAdministrateurProfilSelect(): ElementFinder {
        return this.administrateurProfilSelect;
    }

    async getAdministrateurProfilSelectedOption() {
        return this.administrateurProfilSelect.element(by.css('option:checked')).getText();
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

export class ProfilDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-profil-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-profil'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
