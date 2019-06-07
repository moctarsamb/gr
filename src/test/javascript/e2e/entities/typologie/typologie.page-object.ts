import { element, by, ElementFinder } from 'protractor';

export class TypologieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-typologie div table .btn-danger'));
    title = element.all(by.css('jhi-typologie div h2#page-heading span')).first();

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

export class TypologieUpdatePage {
    pageTitle = element(by.id('jhi-typologie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    libelleInput = element(by.id('field_libelle'));
    conditionInput = element(by.id('field_condition'));
    traitementInput = element(by.id('field_traitement'));

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

    async setConditionInput(condition) {
        await this.conditionInput.sendKeys(condition);
    }

    async getConditionInput() {
        return this.conditionInput.getAttribute('value');
    }

    async setTraitementInput(traitement) {
        await this.traitementInput.sendKeys(traitement);
    }

    async getTraitementInput() {
        return this.traitementInput.getAttribute('value');
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

export class TypologieDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-typologie-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-typologie'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
