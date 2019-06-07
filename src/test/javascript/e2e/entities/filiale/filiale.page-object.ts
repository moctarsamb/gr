import { element, by, ElementFinder } from 'protractor';

export class FilialeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-filiale div table .btn-danger'));
    title = element.all(by.css('jhi-filiale div h2#page-heading span')).first();

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

export class FilialeUpdatePage {
    pageTitle = element(by.id('jhi-filiale-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    libelleInput = element(by.id('field_libelle'));
    environnementSelect = element(by.id('field_environnement'));
    profilSelect = element(by.id('field_profil'));

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

    async profilSelectLastOption() {
        await this.profilSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async profilSelectOption(option) {
        await this.profilSelect.sendKeys(option);
    }

    getProfilSelect(): ElementFinder {
        return this.profilSelect;
    }

    async getProfilSelectedOption() {
        return this.profilSelect.element(by.css('option:checked')).getText();
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

export class FilialeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-filiale-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-filiale'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
