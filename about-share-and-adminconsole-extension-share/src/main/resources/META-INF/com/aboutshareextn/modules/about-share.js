if (typeof ShareModuleComponents == "undefined" || !ShareModuleComponents) {
	var ShareModuleComponents = {};
}

if (typeof ShareModuleComponents.module == "undefined"
		|| !ShareModuleComponents.module) {
	ShareModuleComponents.module = {};
}

(function() {
	var Dom = YAHOO.util.Dom;
	ShareModuleComponents.module.AboutShare = function(containerId) {
		var instance = Alfresco.util.ComponentManager.get(this.id);
		if (instance !== null) {
			throw new Error("An instance of ShareModuleComponents.module.AboutShare already exists.");
		}

		ShareModuleComponents.module.AboutShare.superclass.constructor.call(this, containerId);

		return this;
	};

	YAHOO.extend(
					ShareModuleComponents.module.AboutShare,
					Alfresco.module.AboutShare,
					{
						scrollpos : 0,
						show : function AS_show() {
							if (this.widgets.panel) {
								this.widgets.panel.show();
							} else {
								Alfresco.util.Ajax
										.request({
											url : Alfresco.constants.URL_SERVICECONTEXT + "aboutshareextn/modules/about-share",
											dataObj : {
												htmlid : this.id
											},
											successCallback : {
												fn : this.onTemplateLoaded,
												scope : this
											},
											execScripts : true,
											failureMessage : "Could not load About Share template"
										});
							}
						}
					});

})();

ShareModuleComponents.module.getAboutShareInstance = function() {
	var instanceId = "ShareModuleComponents-alfresco-AboutShare-instance";
	return Alfresco.util.ComponentManager.get(instanceId)
			|| new ShareModuleComponents.module.AboutShare(instanceId);
};