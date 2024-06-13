# Proxy Content

This servlet show cases a way to use the Java HTTP Servlet library to forward requests to internal resources within the repository.

# How to build

    mvn clean install

# How to install

1. Build the bundle
2. Go to /system/console/bundles
3. Install > Provide Bundle > Check Start Bundle > Install or Update

# Mechanics of the solution

The ProxyServlet retrieves the resource that was requested and gets the property `proxyPath` and then uses the `RequestDispatcher` to forward the request to another resource.

This in turn allows for the resource that is being rendered to be rendered using the correct scripts.

# Example proxy with Local Content 

1. Install wknd project
2. Create a node `/apps(sling:Folder)/wknd-site(nt:folder)`
3. Copy `/content/wknd/language-masters/en/magazine` to `/apps/wknd-site`
4. Create a node called `/content(sling:OrderedFolder)/proxies(sling:Folder)/magazine(nt:unstructured)`
5. Set the following properties on the magazine node
    - proxyPath=/apps/wknd-site/magazine
    - sling:resourceType=aem/support/proxy
6. Make a GET request to `/content/proxies/magazine.html` the rendered page will be the one copied to `/apps/wknd-site/magazine`

# Example proxy with Remote Content 

1. Create a node `/content(sling:Orderedfolder)/licenses(nt:unstructured)/eula(nt:unstructured)`
2. Set the following properties under the eula node
       - `isExternal={Boolean}true`
       - `proxyPath=https://www.fornav.com/documents/EULA.pdf`
       - `sling:resourceType=aem/support/proxy`
3. Upload an asset to the dam
4. Put a node property the metadata node with the following value `xmpRights:WebStatement=/content/licenses/eula.html` (see https://experienceleague.adobe.com/en/docs/experience-manager-brand-portal/using/download/manage-digital-rights-of-assets)
5. Go download the asset you uploaded in step 3 and you will be presented with https://www.fornav.com/documents/EULA.pdf
