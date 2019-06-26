package com.rajesh.realmpractice.database.migration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigrationHelper implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            updateUserSchema(schema);
        }
    }

    private void updateUserSchema(RealmSchema schema) {
        final RealmObjectSchema dbBookSchema = schema.get("DbBook");
        if (dbBookSchema != null) {
            dbBookSchema.addField("published", String.class);
        }
    }
}
