import { element, by, ElementFinder } from 'protractor';

export class ServeurComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-serveur div table .btn-danger'));
    title = element.all(by.css('jhi-serveur div h2#page-heading span')).first();

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

export class ServeurUpdatePage {
    pageTitle = element(by.id('jhi-serveur-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomInput = element(by.id('field_nom'));
    adresseIpInput = element(by.id('field_adresseIp'));
    loginInput = element(by.id('field_login'));
    motDePasseInput = element(by.id('field_motDePasse'));
    repertoireDepotsInput = element(by.id('field_repertoireDepots'));
    repertoireResultatsInput = element(by.id('field_repertoireResultats'));
    repertoireParametresInput = element(by.id('field_repertoireParametres'));
    estActifInput = element(by.id('field_estActif'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomInput(nom) {
        await this.nomInput.sendKeys(nom);
    }

    async getNomInput() {
        return this.nomInput.getAttribute('value');
    }

    async setAdresseIpInput(adresseIp) {
        await this.adresseIpInput.sendKeys(adresseIp);
    }

    async getAdresseIpInput() {
        return this.adresseIpInput.getAttribute('value');
    }

    async setLoginInput(login) {
        await this.loginInput.sendKeys(login);
    }

    async getLoginInput() {
        return this.loginInput.getAttribute('value');
    }

    async setMotDePasseInput(motDePasse) {
        await this.motDePasseInput.sendKeys(motDePasse);
    }

    async getMotDePasseInput() {
        return this.motDePasseInput.getAttribute('value');
    }

    async setRepertoireDepotsInput(repertoireDepots) {
        await this.repertoireDepotsInput.sendKeys(repertoireDepots);
    }

    async getRepertoireDepotsInput() {
        return this.repertoireDepotsInput.getAttribute('value');
    }

    async setRepertoireResultatsInput(repertoireResultats) {
        await this.repertoireResultatsInput.sendKeys(repertoireResultats);
    }

    async getRepertoireResultatsInput() {
        return this.repertoireResultatsInput.getAttribute('value');
    }

    async setRepertoireParametresInput(repertoireParametres) {
        await this.repertoireParametresInput.sendKeys(repertoireParametres);
    }

    async getRepertoireParametresInput() {
        return this.repertoireParametresInput.getAttribute('value');
    }

    getEstActifInput() {
        return this.estActifInput;
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

export class ServeurDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-serveur-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-serveur'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
