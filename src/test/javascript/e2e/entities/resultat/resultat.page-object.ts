import { element, by, ElementFinder } from 'protractor';

export class ResultatComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-resultat div table .btn-danger'));
    title = element.all(by.css('jhi-resultat div h2#page-heading span')).first();

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

export class ResultatUpdatePage {
    pageTitle = element(by.id('jhi-resultat-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    statusSelect = element(by.id('field_status'));
    etatSelect = element(by.id('field_etat'));
    champsRechercheSelect = element(by.id('field_champsRecherche'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setEtatSelect(etat) {
        await this.etatSelect.sendKeys(etat);
    }

    async getEtatSelect() {
        return this.etatSelect.element(by.css('option:checked')).getText();
    }

    async etatSelectLastOption() {
        await this.etatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async champsRechercheSelectLastOption() {
        await this.champsRechercheSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async champsRechercheSelectOption(option) {
        await this.champsRechercheSelect.sendKeys(option);
    }

    getChampsRechercheSelect(): ElementFinder {
        return this.champsRechercheSelect;
    }

    async getChampsRechercheSelectedOption() {
        return this.champsRechercheSelect.element(by.css('option:checked')).getText();
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

export class ResultatDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-resultat-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-resultat'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
