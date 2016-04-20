package dpfmanager.shell.core.adapter;

import dpfmanager.shell.application.launcher.noui.ApplicationParameters;
import dpfmanager.shell.application.launcher.noui.ParametersMessage;
import dpfmanager.shell.core.messages.DpfMessage;

/**
 * Created by Adrià Llorens on 07/04/2016.
 */
public abstract class DpfSpringController {

  protected ApplicationParameters params;

  public void handleDpfMessage(DpfMessage message) {
    if (message.isTypeOf(ParametersMessage.class)) {
      params = message.getTypedMessage(ParametersMessage.class).getParams();
    } else {
      handleMessage(message);
    }
  }

  public abstract void handleMessage(DpfMessage message);

}
