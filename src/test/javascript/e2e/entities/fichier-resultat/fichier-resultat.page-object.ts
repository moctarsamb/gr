import { element, by, ElementFinder } from 'protractor';

export class FichierResultatComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-fichier-resultat div table .btn-danger'));
    title = element.all(by.css('jhi-fichier-resultat div h2#page-heading span')).first();

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

export class FichierResultatUpdatePage {
    pageTitle = element(by.id('jhi-fichier-resultat-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    cheminFichierInput = element(by.id('field_cheminFichier'));
    formatSelect = element(by.id('field_format'));
    avecStatistiquesInput = element(by.id('field_avecStatistiques'));
    resultatSelect = element(by.id('field_resultat'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCheminFichierInput(cheminFichier) {
        await this.cheminFichierInput.sendKeys(cheminFichier);
    }

    async getCheminFichierInput() {
        return this.cheminFichierInput.getAttribute('value');
    }

    async setFormatSelect(format) {
        await this.formatSelect.sendKeys(format);
    }

    async getFormatSelect() {
        return this.formatSelect.element(by.css('option:checked')).getText();
    }

    async formatSelectLastOption() {
        await this.formatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    getAvecStatistiquesInput() {
        return this.avecStatistiquesInput;
    }

    async resultatSelectLastOption() {
        await this.resultatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async resultatSelectOption(option) {
        await this.resultatSelect.sendKeys(option);
    }

    getResultatSelect(): ElementFinder {
        return this.resultatSelect;
    }

    async getResultatSelectedOption() {
        return this.resultatSelect.element(by.css('option:checked')).getText();
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

export class FichierResultatDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-fichierResultat-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-fichierResultat'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
