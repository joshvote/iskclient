Ext.application({
    name : 'iskclient',


    launch : function() {
        var store = Ext.create('Ext.data.Store', {
            autoLoad : true,
            fields : [ 'url', 'rating'],
            proxy : {
                type : 'ajax',
                url : 'getSimilarImages.do',
                reader : {
                    type : 'json',
                    root : 'data'
                }
            }
        });


        /**
         * Add all the panels to the viewport
         */
        var viewport = Ext.create('Ext.container.Viewport', {
            layout:'border',
            items:[{
                xtype: 'panel',
                title: 'Images (Click to find similar images)',
                layout: 'fit',
                region: 'center',
                padding: '5 5 5 0',
                items: {
                    xtype: 'imageview',
                    store : store,
                    listeners : {
                        itemclick : function(view, record, item, index) {
                            var id = record.get('id');
                            store.getProxy().extraParams = {'imageId' : id};
                            store.load();
                        }
                    }
                }
            }]
        });

    }
});