import { element, by, ElementFinder } from 'protractor';

export class JointureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-jointure div table .btn-danger'));
    title = element.all(by.css('jhi-jointure div h2#page-heading span')).first();

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

export class JointureUpdatePage {
    pageTitle = element(by.id('jhi-jointure-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    critereSelect = element(by.id('field_critere'));
    fluxSelect = element(by.id('field_flux'));
    typeJointureSelect = element(by.id('field_typeJointure'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async critereSelectLastOption() {
        await this.critereSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async critereSelectOption(option) {
        await this.critereSelect.sendKeys(option);
    }

    getCritereSelect(): ElementFinder {
        return this.critereSelect;
    }

    async getCritereSelectedOption() {
        return this.critereSelect.element(by.css('option:checked')).getText();
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

    async typeJointureSelectLastOption() {
        await this.typeJointureSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typeJointureSelectOption(option) {
        await this.typeJointureSelect.sendKeys(option);
    }

    getTypeJointureSelect(): ElementFinder {
        return this.typeJointureSelect;
    }

    async getTypeJointureSelectedOption() {
        return this.typeJointureSelect.element(by.css('option:checked')).getText();
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

export class JointureDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-jointure-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-jointure'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
