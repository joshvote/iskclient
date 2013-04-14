/**
 * Allows you to render straight images in a view form.
 *
 * Adapted from Ext.org.ImageView
 */
Ext.define('iskclient.view.ImageView', {
    extend : 'Ext.view.View',
    alias : 'widget.imageview',
    requires : [ 'Ext.data.Store' ],
    tpl : [
            '<tpl for=".">',
            '<div class="thumb-wrap">',
            '<div class="thumb">',
            (!Ext.isIE6 ? '<img src="{url}" />'
                    : '<div style="width:76px;height:76px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'{url}\')"></div>'),
            '</div>', '<span>Rating: {rating}</span>', '</div>', '</tpl>' ],
    itemSelector : 'div.thumb-wrap',
    multiSelect : true,
    singleSelect : false,
    cls : 'x-image-view',
    autoScroll : true,
    initComponent : function() {
        this.callParent();
    }
});
