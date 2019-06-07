import { element, by, ElementFinder } from 'protractor';

export class ParametrageApplicationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-parametrage-application div table .btn-danger'));
    title = element.all(by.css('jhi-parametrage-application div h2#page-heading span')).first();

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

export class ParametrageApplicationUpdatePage {
    pageTitle = element(by.id('jhi-parametrage-application-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomFichierInput = element(by.id('field_nomFichier'));
    cheminFichierInput = element(by.id('field_cheminFichier'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomFichierInput(nomFichier) {
        await this.nomFichierInput.sendKeys(nomFichier);
    }

    async getNomFichierInput() {
        return this.nomFichierInput.getAttribute('value');
    }

    async setCheminFichierInput(cheminFichier) {
        await this.cheminFichierInput.sendKeys(cheminFichier);
    }

    async getCheminFichierInput() {
        return this.cheminFichierInput.getAttribute('value');
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

export class ParametrageApplicationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-parametrageApplication-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-parametrageApplication'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
