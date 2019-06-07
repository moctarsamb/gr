import { element, by, ElementFinder } from 'protractor';

export class StructureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-structure div table .btn-danger'));
    title = element.all(by.css('jhi-structure div h2#page-heading span')).first();

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

export class StructureUpdatePage {
    pageTitle = element(by.id('jhi-structure-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    libelleInput = element(by.id('field_libelle'));
    filialeSelect = element(by.id('field_filiale'));

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

export class StructureDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-structure-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-structure'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
