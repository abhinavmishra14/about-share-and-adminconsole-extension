<@markup id="aboutshareextn-css" target="css" action="after" scope="global">
   <#-- CSS Dependencies -->
   <@link href="${url.context}/res/com/aboutshareextn/modules/about-share.css" group="footer"/>
</@>

<@markup id="aboutshareextn-js" target="js" action="after" scope="global">
   <@script src="${url.context}/res/com/aboutshareextn/modules/about-share.js" group="footer"/>
</@>

<@markup id="aboutshareextn-widgets" target="widgets" action="replace" scope="global">
   <@createWidgets/>
</@>

<@markup id="aboutshareextn-html" target="html" action="replace" scope="global">
   <@uniqueIdDiv>
      <#assign fc=config.scoped["Edition"]["footer"]>
      <div class="footer ${fc.getChildValue("css-class")!"footer-com"}">
         <span class="copyright">
            <a href="#" onclick="ShareModuleComponents.module.getAboutShareInstance().show(); return false;"><img src="${url.context}/res/components/images/${fc.getChildValue("logo")!"alfresco-logo.svg"}" alt="${fc.getChildValue("alt-text")!"Alfresco Community"}" border="0"/></a>
            <#if licenseHolder != "" && licenseHolder != "UNKNOWN">
               <span class="licenseHolder">${msg("label.licensedTo")} ${licenseHolder}</span><br>
            </#if>
            <span>${msg(fc.getChildValue("label")!"label.copyright")}</span>
         </span>
      </div>
   </@>
</@>
