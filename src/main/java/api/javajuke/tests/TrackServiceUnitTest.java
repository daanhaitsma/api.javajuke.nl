package api.javajuke.tests;

import api.javajuke.data.TrackRepository;
import api.javajuke.data.model.Track;
import api.javajuke.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackServiceUnitTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackService trackService;

    @Test
    public void testGetTrack() {
        Track dummyTrack = new Track("/example/path");
        when(trackRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyTrack));

        Track track = trackService.getTrack(1);

        Assert.assertEquals(track.getPath(), "/example/path");
    }
}