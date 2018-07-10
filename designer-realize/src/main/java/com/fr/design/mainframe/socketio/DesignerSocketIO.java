package com.fr.design.mainframe.socketio;

import com.fr.decision.webservice.utils.DecisionServiceConstants;
import com.fr.design.mainframe.loghandler.DesignerLogHandler;
import com.fr.general.LogRecordTime;
import com.fr.general.LogUtils;
import com.fr.general.http.HttpToolbox;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.log.FineLoggerFactory;
import com.fr.third.guava.base.Optional;
import com.fr.third.guava.primitives.Ints;
import com.fr.workspace.WorkContext;
import com.fr.workspace.Workspace;
import com.fr.workspace.base.WorkspaceConstants;
import com.fr.workspace.engine.server.rpc.netty.RemoteCallClient;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class DesignerSocketIO {

    private static Optional<Socket> socketIO = Optional.absent();

    private static final Emitter.Listener printLog = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            try {
                LogRecordTime[] logRecordTimes = LogUtils.readXMLLogRecords(new ByteArrayInputStream((byte[]) objects[0]));
                for (LogRecordTime logRecordTime : logRecordTimes) {
                    DesignerLogHandler.getInstance().printRemoteLog(logRecordTime);
                }
            } catch (Exception e) {
                FineLoggerFactory.getLogger().error(e.getMessage(), e);
            }
        }
    };

    public static void close() {
        if (socketIO.isPresent()) {
            socketIO.get().close();
            socketIO = Optional.absent();
        }
    }

    public static void update() {
        Workspace current = WorkContext.getCurrent();
        if (current.isLocal()) {
            return;
        }
        try {
            String uri = getSocketUri(current);
            socketIO = Optional.of(IO.socket(new URI(uri)));
            socketIO.get().on(WorkspaceConstants.WS_LOGRECORD, printLog);
            socketIO.get().connect();
        } catch (Exception e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
        }
    }

    private static String getSocketUri(Workspace current) throws IOException {
        URL url = new URL(current.getPath());
        int port = getPort(current);
        return String.format("http://%s:%s%s?%s=%s",
                url.getHost(),
                port,
                WorkspaceConstants.WS_NAMESPACE,
                DecisionServiceConstants.WEB_SOCKET_TOKEN_NAME,
                RemoteCallClient.getInstance().getToken());
    }

    private static int getPort(Workspace current) throws IOException {
        String url = current.getPath() + WorkspaceConstants.CONTROLLER_PREFIX + WorkspaceConstants.CONTROLLER_SOCKETIO_PORT;
        try {
            String portStr = HttpToolbox.get(url);
            JSONObject jsonObject = new JSONObject(portStr);
            return Ints.tryParse(jsonObject.optString("data"));
        } catch (JSONException e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}