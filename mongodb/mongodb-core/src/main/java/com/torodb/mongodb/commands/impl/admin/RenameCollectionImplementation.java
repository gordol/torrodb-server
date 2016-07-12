/*
 *     This file is part of ToroDB.
 *
 *     ToroDB is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ToroDB is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with ToroDB. If not, see <http://www.gnu.org/licenses/>.
 *
 *     Copyright (c) 2014, 8Kdata Technology
 *     
 */

package com.torodb.mongodb.commands.impl.admin;

import com.eightkdata.mongowp.ErrorCode;
import com.eightkdata.mongowp.Status;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.admin.RenameCollectionCommand.RenameCollectionArgument;
import com.eightkdata.mongowp.server.api.Command;
import com.eightkdata.mongowp.server.api.Request;
import com.eightkdata.mongowp.server.api.tools.Empty;
import com.torodb.core.exceptions.user.UserException;
import com.torodb.mongodb.commands.impl.WriteTorodbCommandImpl;
import com.torodb.mongodb.core.WriteMongodTransaction;

public class RenameCollectionImplementation implements WriteTorodbCommandImpl<RenameCollectionArgument, Empty> {

    @Override
    public Status<Empty> apply(Request req, Command<? super RenameCollectionArgument, ? super Empty> command,
            RenameCollectionArgument arg, WriteMongodTransaction context) {
        try {
            if (arg.isDropTarget()) {
                context.getTorodTransaction().dropCollection(arg.getToDatabase(), arg.getToCollection());
            }
            
            context.getTorodTransaction().renameCollection(arg.getFromDatabase(), arg.getFromCollection(), arg.getToDatabase(), arg.getToCollection());
        } catch (UserException ex) {
            return Status.from(ErrorCode.COMMAND_FAILED, ex.getLocalizedMessage());
        }

        return Status.ok();
    }

}
