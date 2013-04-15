Ext.application({
    name : 'iskclient',


    launch : function() {

        var History = window.History;
        // Bind to StateChange Event
        History.Adapter.bind(window,'statechange',function(){ // Note: We are using statechange instead of popstate
            var State = History.getState(); // Note: We are using History.getState() instead of event.state
            History.log(State.data, State.title, State.url);
        });

        var urlParams = Ext.Object.fromQueryString(window.location.search.substring(1));

        var store = Ext.create('Ext.data.Store', {
            autoLoad : true,
            fields : [ 'url', 'rating'],
            proxy : {
                type : 'ajax',
                url : 'getSimilarImages.do',
                extraParams : {'imageId' : urlParams.imageId, 'dbId' : (urlParams.dbId ? urlParams.dbId : 1)},
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
                xtype : 'form',
                region : 'north',
                items : [{
                    xtype : 'displayfield',
                    anchor : '100%',
                    style : 'font-size: 12px;',
                    value : 'The images below are randomly chosen from one of the available image databases. Click an image to load the most similar images from the same database as judged by <a href="http://www.imgseek.net" target="_blank">imgSeek</a>'
                },{
                    xtype : 'combo',
                    fieldLabel : 'Image Database',
                    id : 'combo-dbid', //Bad practise - real apps should use itemId
                    displayField: 'name',
                    valueField: 'dbId',
                    value : (urlParams.dbId ? urlParams.dbId : 1),
                    store : Ext.create('Ext.data.Store', {
                        fields : ['name', 'dbId'],
                        data : [
                            {'name':'Caltech 256 - Random Images', 'dbId' : 1},
                            {'name':'Crystal Droplets', 'dbId' : 2}]
                    }),
                    listeners : {
                        select : function(combo, records) {
                            var ds = Ext.getCmp('view-imageview').getStore();
                            if (!records || records.length === 0) {
                                ds.removeAll();
                                return;
                            }

                            var dbId = records[0].get('dbId');
                            ds.getProxy().extraParams = {'imageId' : '', 'dbId' : dbId};
                            ds.load();

                            History.pushState({dbId:dbId}, "", "?dbId=" + dbId);
                        }
                    }
                }]
            },{
                xtype: 'panel',
                title: 'Images (Click to find similar images)',
                layout: 'fit',
                region: 'center',
                padding: '5 5 5 0',
                items: {
                    xtype: 'imageview',
                    id : 'view-imageview', //Bad practise - real apps should use itemId
                    store : store,
                    listeners : {
                        itemclick : function(view, record, item, index) {
                            var dbId = Ext.getCmp('combo-dbid').getValue();
                            var id = record.get('id');
                            store.getProxy().extraParams = {'imageId' : id, 'dbId' : dbId};
                            store.load();

                            History.pushState({dbId:dbId, imageId:id}, "", "?dbId=" + dbId + "&imageId=" + id);
                        }
                    }
                }
            }]
        });

    }
});