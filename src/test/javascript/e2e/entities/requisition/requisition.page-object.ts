import { element, by, ElementFinder } from 'protractor';

export class RequisitionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-requisition div table .btn-danger'));
    title = element.all(by.css('jhi-requisition div h2#page-heading span')).first();

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

export class RequisitionUpdatePage {
    pageTitle = element(by.id('jhi-requisition-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    numeroArriveeDemandeInput = element(by.id('field_numeroArriveeDemande'));
    numeroPvInput = element(by.id('field_numeroPv'));
    dateSaisiePvInput = element(by.id('field_dateSaisiePv'));
    dateArriveeDemandeInput = element(by.id('field_dateArriveeDemande'));
    dateSaisieDemandeInput = element(by.id('field_dateSaisieDemande'));
    envoiResultatAutomatiqueInput = element(by.id('field_envoiResultatAutomatique'));
    dateReponseInput = element(by.id('field_dateReponse'));
    dateRenvoieResultatInput = element(by.id('field_dateRenvoieResultat'));
    statusSelect = element(by.id('field_status'));
    etatSelect = element(by.id('field_etat'));
    provenanceSelect = element(by.id('field_provenance'));
    structureSelect = element(by.id('field_structure'));
    utilisateurSelect = element(by.id('field_utilisateur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNumeroArriveeDemandeInput(numeroArriveeDemande) {
        await this.numeroArriveeDemandeInput.sendKeys(numeroArriveeDemande);
    }

    async getNumeroArriveeDemandeInput() {
        return this.numeroArriveeDemandeInput.getAttribute('value');
    }

    async setNumeroPvInput(numeroPv) {
        await this.numeroPvInput.sendKeys(numeroPv);
    }

    async getNumeroPvInput() {
        return this.numeroPvInput.getAttribute('value');
    }

    async setDateSaisiePvInput(dateSaisiePv) {
        await this.dateSaisiePvInput.sendKeys(dateSaisiePv);
    }

    async getDateSaisiePvInput() {
        return this.dateSaisiePvInput.getAttribute('value');
    }

    async setDateArriveeDemandeInput(dateArriveeDemande) {
        await this.dateArriveeDemandeInput.sendKeys(dateArriveeDemande);
    }

    async getDateArriveeDemandeInput() {
        return this.dateArriveeDemandeInput.getAttribute('value');
    }

    async setDateSaisieDemandeInput(dateSaisieDemande) {
        await this.dateSaisieDemandeInput.sendKeys(dateSaisieDemande);
    }

    async getDateSaisieDemandeInput() {
        return this.dateSaisieDemandeInput.getAttribute('value');
    }

    getEnvoiResultatAutomatiqueInput() {
        return this.envoiResultatAutomatiqueInput;
    }
    async setDateReponseInput(dateReponse) {
        await this.dateReponseInput.sendKeys(dateReponse);
    }

    async getDateReponseInput() {
        return this.dateReponseInput.getAttribute('value');
    }

    async setDateRenvoieResultatInput(dateRenvoieResultat) {
        await this.dateRenvoieResultatInput.sendKeys(dateRenvoieResultat);
    }

    async getDateRenvoieResultatInput() {
        return this.dateRenvoieResultatInput.getAttribute('value');
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

    async provenanceSelectLastOption() {
        await this.provenanceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async provenanceSelectOption(option) {
        await this.provenanceSelect.sendKeys(option);
    }

    getProvenanceSelect(): ElementFinder {
        return this.provenanceSelect;
    }

    async getProvenanceSelectedOption() {
        return this.provenanceSelect.element(by.css('option:checked')).getText();
    }

    async structureSelectLastOption() {
        await this.structureSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async structureSelectOption(option) {
        await this.structureSelect.sendKeys(option);
    }

    getStructureSelect(): ElementFinder {
        return this.structureSelect;
    }

    async getStructureSelectedOption() {
        return this.structureSelect.element(by.css('option:checked')).getText();
    }

    async utilisateurSelectLastOption() {
        await this.utilisateurSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async utilisateurSelectOption(option) {
        await this.utilisateurSelect.sendKeys(option);
    }

    getUtilisateurSelect(): ElementFinder {
        return this.utilisateurSelect;
    }

    async getUtilisateurSelectedOption() {
        return this.utilisateurSelect.element(by.css('option:checked')).getText();
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

export class RequisitionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-requisition-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-requisition'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
