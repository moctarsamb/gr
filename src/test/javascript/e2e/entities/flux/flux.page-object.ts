import { element, by, ElementFinder } from 'protractor';

export class FluxComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-flux div table .btn-danger'));
    title = element.all(by.css('jhi-flux div h2#page-heading span')).first();

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

export class FluxUpdatePage {
    pageTitle = element(by.id('jhi-flux-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    libelleInput = element(by.id('field_libelle'));
    descriptionInput = element(by.id('field_description'));
    baseSelect = element(by.id('field_base'));

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

export class FluxDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-flux-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-flux'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
